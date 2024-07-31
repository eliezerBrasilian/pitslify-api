package pitslify.api.services.impl;

import pitslify.api.config.FileStorageConfig;
import pitslify.api.exceptions.FileStorageException;
import pitslify.api.records.FileDocument;
import lombok.AllArgsConstructor;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@AllArgsConstructor
@Service
public class FileStorageServiceImpl {

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

    public FileDocument storeFile(MultipartFile file) {
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

            FileDocument fileDocument = new FileDocument(
                    fileName,
                    file.getContentType(),
                    fileData.length,
                    fileData
            );

            return fileDocument;
        } catch (Exception e) {
            throw new FileStorageException(String.format("Could not store file %s. Please try again!", fileName), e);
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
