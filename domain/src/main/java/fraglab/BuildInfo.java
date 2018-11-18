package fraglab;

public class BuildInfo {

    private String gitHash;
    private String timeStamp;

    public BuildInfo() {
    }

    public BuildInfo(String gitHash, String timeStamp) {
        this.gitHash = gitHash;
        this.timeStamp = timeStamp;
    }

    public String getGitHash() {
        return gitHash;
    }

    public void setGitHash(String gitHash) {
        this.gitHash = gitHash;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
