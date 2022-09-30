package enigmaDtos;

import utils.BruteForceLevel;

public class BruteForceDetailsDTO {
    private Integer agents;
    private BruteForceLevel level;
    private Integer taskSize;

    public BruteForceDetailsDTO(Integer agents,BruteForceLevel level,Integer taskSize){
        this.agents = agents;
        this.level = level;
        this.taskSize = taskSize;
    }

    public Integer getAgents() {
        return agents;
    }

    public BruteForceLevel getLevel() {
        return level;
    }

    public Integer getTaskSize() {
        return taskSize;
    }
}
