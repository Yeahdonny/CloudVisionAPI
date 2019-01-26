package cloudvision;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.vision.v1p1beta1.AnnotateImageRequest;
import com.google.cloud.vision.v1p1beta1.AnnotateImageResponse;
import com.google.cloud.vision.v1p1beta1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1p1beta1.EntityAnnotation;
import com.google.cloud.vision.v1p1beta1.FaceAnnotation ;
import com.google.cloud.vision.v1p1beta1.Feature;
import com.google.cloud.vision.v1p1beta1.Feature.Type;
import com.google.cloud.vision.v1p1beta1.Image;
import com.google.cloud.vision.v1p1beta1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
public class Face {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FaceDetectMode("images/smile.jpg");
	
	}

	private static void FaceDetectMode(String filePath) throws IOException {
		// TODO Auto-generated method stub
		List<AnnotateImageRequest> requests = new ArrayList<>();
		ByteString imgBytes =ByteString.readFrom(new FileInputStream(filePath));
		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Type.FACE_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);
		try(ImageAnnotatorClient client = ImageAnnotatorClient.create()){
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();
			
			for(AnnotateImageResponse res : responses){
				if(res.hasError()){
					System.out.printf("Error: %\n", res.getError().getMessage());
					return;
				}
				for(FaceAnnotation annotation : res.getFaceAnnotationsList()){
					System.out.println("Anger : " + annotation.getAngerLikelihood());
					System.out.println("Joy : " + annotation.getJoyLikelihood());
					System.out.println("Surprise : " + annotation.getSurpriseLikelihood());
					System.out.println("Position : " + annotation.getBoundingPoly());
					
				}
			}
		}
	}

}
