package pitslify.api.records;

public record FileDocument(
        String fileName,
        String fileType,
        long size,
        byte[] data
     ){}
