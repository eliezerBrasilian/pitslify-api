package pitslify.api.services;

import pitslify.api.exceptions.CustomFileNotFoundException;
import pitslify.api.exceptions.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file) throws FileStorageException;
    Resource loadFileAsResource(String fileName) throws CustomFileNotFoundException;
}
