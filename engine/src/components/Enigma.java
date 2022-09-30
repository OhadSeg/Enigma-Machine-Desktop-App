package components;

import enigmaDtos.BruteForceConfigDTO;
import generated.CTEEnigma;
import enigmaDtos.TestedMachineDetailsDTO;
import javafx.util.Pair;
import utils.RomanNumbers;

import java.io.*;
import java.util.ArrayList;


public class Enigma implements Serializable {

    private Machine machine;
    private TestedMachineDetailsDTO configuration;

    public Enigma(CTEEnigma cteEnigma) {
        this.machine = new Machine(cteEnigma.getCTEMachine());
    }

    public Machine getMachine() {
        return machine;
    }


    public void setEnigmaMachine(TestedMachineDetailsDTO configuration) {
        this.configuration = configuration;
        machine.insertMachineDetails(configuration);
    }

    public TestedMachineDetailsDTO getConfigurationAsDTO() {
        return configuration;
    }

    public void setRotorsStartingPosBF(ArrayList<Integer> startingPos){
        machine.setStartingPointOfRotorsFromBF(startingPos);
    }

    public void setConfigurationBF(ArrayList<Integer> startingPos, RomanNumbers reflector){
        machine.setStartingPointOfRotorsFromBF(startingPos);
        machine.setReflector(reflector);
    }

    public void reset() {
        machine.reset();
    }

    public ArrayList<Integer> getAllRotorsNotches() {
        return machine.getAllRotorsNotches();
    }

    public Pair execute(String textToCode) {
        long begin = System.nanoTime();
        String textAfterCode = machine.startCodeText(textToCode);
        long end = System.nanoTime();
        Long timeExecute = new Long(end - begin);
        Pair codedTextAndTime = new Pair<>(textAfterCode, timeExecute);

        return codedTextAndTime;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public int getAmountOfAllReflectors() {
        return machine.getAmountOfAllReflectors();
    }

    public int getAmountOfAllRotors() {
        return machine.getAmountOfAllRotors();
    }

    public int getAmountOfRotorsInUse() {
        return machine.getCountRotors();
    }

    public int getAmountOfAllRotorsInXml() {
        return machine.getAmountOfAllRotorsInXml();
    }

    public String getAbc() {
        return machine.getAbc();
    }

    public boolean checkIfAllReflectorsValid() {
        return machine.checkIfAllReflectorsValid();
    }

    public Enigma deepCopy(){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(this);
            byte[] serializedObject1 = bos.toByteArray();
            os.close();

            ByteArrayInputStream bis = new ByteArrayInputStream(serializedObject1);
            ObjectInputStream oInputStream = new ObjectInputStream(bis);
            Enigma enigmaClone = (Enigma) oInputStream.readObject();
            oInputStream.close();
            return enigmaClone;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
            }
    }

    @Override
    public String toString() {
        return machine.toString();
    }
}
