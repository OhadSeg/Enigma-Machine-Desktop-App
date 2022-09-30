package enigmaDtos;

public class CandidateDTO {

    private String candidate;
    private String configuration;
    private int agentId;

    public CandidateDTO(String candidate, String configuration, int agentId) {
        this.candidate = candidate;
        this.configuration = configuration;
        this.agentId = agentId;
    }

    public String getCandidate() {
        return candidate;
    }

    public String getConfiguration() {
        return configuration;
    }

    public int getAgentId() {
        return agentId;
    }
}
