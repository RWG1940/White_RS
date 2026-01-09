package white.rs.domain;

import java.util.List;

public class FileShareRequest {
    private Long sharedWithUserId;
    private String sharePassword;
    private String shareType;
    private String expirationTime;
    private List<Long> fileIds;

    // Getters and Setters

    public Long getSharedWithUserId() {
        return sharedWithUserId;
    }

    public void setSharedWithUserId(Long sharedWithUserId) {
        this.sharedWithUserId = sharedWithUserId;
    }

    public String getSharePassword() {
        return sharePassword;
    }

    public void setSharePassword(String sharePassword) {
        this.sharePassword = sharePassword;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public List<Long> getFileIds() {
        return fileIds;
    }

    public void setFileIds(List<Long> fileIds) {
        this.fileIds = fileIds;
    }
}
