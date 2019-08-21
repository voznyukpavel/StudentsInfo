package studentsinfo3.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

import studentsinfo3.model.Group;
import studentsinfo3.storage.Storage;


public class GroupTransfer extends ByteArrayTransfer {

	private static final String MYTYPENAME  = "Group";
	private static final int MYTYPEID  = registerType(MYTYPENAME);
	private static GroupTransfer _instance = new GroupTransfer();

	private GroupTransfer() {
	}

	public static GroupTransfer getInstance() {
		return _instance;
	}
	@Override
	public void javaToNative(Object object, TransferData transferData) {
		if (object == null || !(object instanceof Group[]))
			return;

		if (isSupportedType(transferData)) {
			Group[] myTypes = (Group[]) object;
			try {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				DataOutputStream writeOut = new DataOutputStream(out);		
				for (int i = 0, length = myTypes.length; i < length; i++) {	
					byte[] byteName = myTypes[i].getName().getBytes();				
					writeOut.writeInt(byteName.length);
					writeOut.write(byteName);
				}			
				byte[] buffer = out.toByteArray();
				writeOut.close();
				super.javaToNative(buffer, transferData);
			} catch (IOException e) {
			}
			
		}
	}
	@Override
	public Object nativeToJava(TransferData transferData) {
		if (isSupportedType(transferData)) {
			byte[] buffer = (byte[]) super.nativeToJava(transferData);
			if (buffer == null)
				return null;

			Group[] myData = new Group[0];
			try {
				ByteArrayInputStream in = new ByteArrayInputStream(buffer);
				DataInputStream readIn = new DataInputStream(in);
				
				while (readIn.available() > 20) {
					
					int size = readIn.readInt();
					
					byte[] name = new byte[size];
					
					readIn.read(name);
					String groupname=new String(name);
					Group datum = new Group(Storage.getRoot(),groupname);
			
					Group[] newMyData = new Group[myData.length + 1];
					System.arraycopy(myData, 0, newMyData, 0, myData.length);
					newMyData[myData.length] = datum;
					myData = newMyData;
				}
				readIn.close();
			} catch (IOException ex) {
				return null;
			}
			return myData;
		}
		return null;
	}

	@Override
	protected int[] getTypeIds() {
		return new int[] { MYTYPEID  };
	}

	@Override
	protected String[] getTypeNames() {
		return new String[] { MYTYPENAME };
	}
}
