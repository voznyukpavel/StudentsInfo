package studentsinfo3.model;

import org.eclipse.swt.graphics.Image;

public class PhotoData {
    private Image photo;
    private String imageWay;
   
    public PhotoData(){
    	
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
}
