package studentsinfo3.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class PhotoData {
	private Image photo;
	private String imageWay;

	public PhotoData() {

	}

	public PhotoData(Image photo, String imageWay) {
		super();
		this.photo = photo;
		this.imageWay = imageWay;
	}

	public Image getPhoto() {
		return photo;
	}

	public String getImageWay() {
		return imageWay;
	}

	public void setPhoto(String imageWay) {
		try {
			this.photo = new Image(Display.getCurrent(), (new FileInputStream(imageWay)));
			this.imageWay = imageWay;
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();
		}
	}
}
