import java.io.*;
import java.math.*;
import java.security.*;
import java.util.*;

public class Utility{
	public static final String CHECKSUM_ALGORITHM = "SHA-256";
    public static final String CHECKSUM_FILE_EXTENSION = "chksum";

	public Utility(){
	}

	/**
     * @param ser The Serializable to be saved to saveFile.
     * @param saveFile The File to save ser to.
     * @return Boolean value on whether the save was successful or not.
     */
    public static boolean saveSerializable(Serializable ser, File saveFile){
        if(ser == null || saveFile == null){
            return false;
        }

        // Print the Serializable - with its checksum - out to file
        boolean isValidFile = saveFile.isFile() || ( !saveFile.exists() && !saveFile.isDirectory() );
        if(isValidFile){
            try{
                // Serialize the Serializable
                FileOutputStream fileStream = new FileOutputStream(saveFile);
                ObjectOutputStream oos = new ObjectOutputStream(fileStream);
                oos.writeObject(ser);
                oos.flush();
                oos.reset();
                fileStream.flush();

                oos.close();
                fileStream.close();


                // Calculate the checksum for this Object
                BigInteger checksum = Utility.calculateChecksum(ser);

                // Get the appropriate checksum file
                String checksumFileName = saveFile.getName() + "." + Utility.CHECKSUM_FILE_EXTENSION;
                File checksumFile = new File(checksumFileName);

                // Write the checksum out to the appropriate checksum file
                FileWriter checksumFileWriter = new FileWriter(checksumFile);
                checksumFileWriter.write(checksum.toString(16));
                checksumFileWriter.write(" ");
                checksumFileWriter.write(saveFile.getName());
                checksumFileWriter.close();
                
                // Return
                return true;
            }
            catch(Exception e){
                e.printStackTrace();
                System.exit(1);
                return false;
            }
        }

        return false;
    }

    /**
     * @param loadFile The File containing a serialized Serializable object.
     * @return De-serialized Serializable object.
     * @throws ChecksumValueException
     * @throws InvalidChecksumFileNameException
     */
    public static Serializable loadSerializable(File loadFile) throws ChecksumValueException, InvalidChecksumFileNameException{
        if(loadFile == null){
            return null;
        }

        // Pull the checksum and the Serializable out of the loadFile
        boolean isValidFile = loadFile.isFile();
        if(isValidFile){
            try{
                // ----------------------------------- //
                // Read-in the Serializable and the Checksum //
                // ----------------------------------- //
                // Read-in the Serializable itself
                FileInputStream fileStream = new FileInputStream(loadFile);
                ObjectInputStream oos = new ObjectInputStream(fileStream);
                
                Serializable resultObj = (Serializable) oos.readObject();

                oos.close();
                fileStream.close();

                // Read-in the checksum
                String checksumFileName = loadFile.getName() + "." + Utility.CHECKSUM_FILE_EXTENSION;
                File checksumFile = new File(checksumFileName);
                Scanner scanner = new Scanner(checksumFile);

                String checksumString = scanner.next();
                String checksumIntendedFileName = scanner.nextLine().trim();
                scanner.close();

                if(!loadFile.getName().equals(checksumIntendedFileName)){
                	String message = String.format("The provided file name '%s' does not match the expected file name '%s'.", loadFile.getName(), checksumIntendedFileName);
                    throw new InvalidChecksumFileNameException(message);
                }

                BigInteger checksumFromFile = new BigInteger(checksumString, 16);

                // --------------------- //
                // Validate the checksum //
                // --------------------- //
                // Calculate the checksum of the Serializable that was just read-in
                BigInteger calculatedChecksum = Utility.calculateChecksum(resultObj);

                // Compare the calculated checksum to the read-in checksum
                if(calculatedChecksum.compareTo(checksumFromFile) != 0){
                    String message = String.format("The checksum contained in '%s' does not match the checksum calculated using that file's contents; "
                       + "this save file is CORRUPTED or has been TAMPERED WITH.", loadFile.getName());
                    throw new ChecksumValueException(message);
                }
                else{
                    return resultObj;
                }
            }
            catch(IOException e){
                e.printStackTrace();
                System.exit(1);
                return null;
            }
            catch(ClassNotFoundException e){
                e.printStackTrace();
                System.exit(1);
                return null;
            }
        }

        return null;
    }

    /**
     * @see Serializable
     * @param ser The Serializable object that will have a checksum generated for it. This object MUST implement Serializable.
     * @return A BigInteger representing the checksum for the given Serializable object, using the algorithm specified by Utility.CHECKSUM_ALGORITHM.
     */
    public static BigInteger calculateChecksum(Serializable ser) {
        if (ser == null) {
          return null;   
        }

        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(ser);
            oos.close();
            bos.close(); // This doesn't do anything, but it's good habit to explicitly close every Stream you use

            
            MessageDigest m = MessageDigest.getInstance(Utility.CHECKSUM_ALGORITHM);
            m.update(bos.toByteArray());

            BigInteger result = new BigInteger(1, m.digest());

            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }



    public static class ChecksumValueException extends Exception {
    	
    	public ChecksumValueException(String message){
    		super(message);
    	}
    }

    public static class InvalidChecksumFileNameException extends Exception {

    	public InvalidChecksumFileNameException(String message){
    		super(message);
    	}
    }
}