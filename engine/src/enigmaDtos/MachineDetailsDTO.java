package enigmaDtos;

import components.Machine;

public class MachineDetailsDTO {
    String rotorsInUse;
    String rotorsInitialPositions;
    String reflectorInUse;
    String plugsBoardInUse;

    public MachineDetailsDTO(String rotorsFromUser, String rotorsInitalPosFromUser, String reflectorFromUser, String plugBoardFromUser) {
        this.rotorsInUse = rotorsFromUser.toUpperCase();
        this.rotorsInitialPositions = rotorsInitalPosFromUser.toUpperCase();
        this.reflectorInUse = reflectorFromUser.toUpperCase();
        this.plugsBoardInUse = plugBoardFromUser.toUpperCase();
    }

    public String getRotorsInUse() {
        return rotorsInUse;
    }

    public String getRotorsInitialPositions() {
        return rotorsInitialPositions;
    }

    public String getReflectorInUse() {
        return reflectorInUse;
    }

    public String getPlugsBoardInUse() {
        return plugsBoardInUse;
    }
}
