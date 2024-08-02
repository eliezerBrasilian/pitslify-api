package pitslify.api.services.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import pitslify.api.config.FileStorageConfig;
import pitslify.api.exceptions.CustomFileNotFoundException;
import pitslify.api.exceptions.FileStorageException;
import pitslify.api.records.FileDocument;
import lombok.AllArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pitslify.api.repositories.AppRepository;
import pitslify.api.services.FileStorageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@AllArgsConstructor
@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImpl(FileStorageConfig fileStorageConfig) {
        var path = Paths.get(fileStorageConfig.getUpload_dir())
                .toAbsolutePath()
                .normalize();

        this.fileStorageLocation = path;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create " +
                    "directory where the uploaded files will be stored", e);
        }
    }

    public FileDocument storeFile(MultipartFile file, String id, String type) {
        var fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException(
                        String.format("Filename contains invalid path sequence: %s", fileName)
                );
            }

            byte[] fileData = file.getBytes();
            if (file.getSize() >= 2 * 1024 * 1024 && isImage(file)) {
                fileData = reduceImageQuality(file);
            }

           return new FileDocument(
                    fileName,
                    file.getContentType(),
                    fileData.length,
                    fileData
            );

            //return fileName;
        } catch (Exception e) {
            throw new FileStorageException(String.format("Could not store file %s. Please try again!", fileName), e);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName){
        try{
            var filePath = this.fileStorageLocation.resolve(fileName);
            var resource = new UrlResource(filePath.toUri());
            if(resource.exists()){
                return resource;
            }else{
                throw new CustomFileNotFoundException("File not found");
            }
        }catch (Exception e){
            throw new CustomFileNotFoundException("File not found",e);
        }
    }

    private boolean isImage(MultipartFile file) {
        return file.getContentType().startsWith("image");
    }

    private byte[] reduceImageQuality(MultipartFile file) throws Exception {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, originalImage.getWidth() / 2, originalImage.getHeight() / 2);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);
        return baos.toByteArray();
    }
}
