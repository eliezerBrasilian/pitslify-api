package pitslify.api.services;

import pitslify.api.exceptions.CustomFileNotFoundException;
import pitslify.api.exceptions.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import pitslify.api.records.FileDocument;

public interface FileStorageService {
    FileDocument storeFile(MultipartFile file, String id, String type) throws FileStorageException;
    Resource loadFileAsResource(String fileName) throws CustomFileNotFoundException;
}
