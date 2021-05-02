package unittests;

import renderer.*;
import primitives.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class ImageWriterTest {

	@Test
	public void testImageWriter() {
		// ============ Equivalence Partitions Tests ==============
        // TC01: write an image of a simple colored grid over a colored background
		
		ImageWriter imgwrt = new ImageWriter("blue_net", 800, 500);
		//print grid
		//interval = 50 / 800/16 = 50, 500/10 = 50
		for(int i = 0; i< imgwrt.getNx(); i++) {
			for(int j = 0; j < imgwrt.getNy(); j++) {
				if(i%50==0&&j%50==0)
					imgwrt.writePixel(i, j, new Color(236, 6, 33));
				else if(i%50==0 || j%50 == 0) 
					imgwrt.writePixel(i, j, new Color(212, 25, 135));
				else 
					imgwrt.writePixel(i, j, new Color(23, 238, 40));
			}
		}
		imgwrt.writeToImage();
	}

}
