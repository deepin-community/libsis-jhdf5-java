/* Test class based on the sample code at https://wiki-bsse.ethz.ch/pages/viewpage.action?pageId=26609113
 * and amended for the Deb package by Tim Booth.  Running this assures us thet the JNI library can
 * be loaded and that the code is at least somewhat functional.
 * This is very simple and therefore serves as a superficial test for autopkgtest.
 */

import ch.systemsx.cisd.hdf5.HDF5Factory;
import ch.systemsx.cisd.hdf5.IHDF5SimpleWriter;
import ch.systemsx.cisd.hdf5.IHDF5SimpleReader;

public class ReadWriteTest {

    public static void main(String[] args) {

	//Make an array
	double[] mydata = new double[1000];
	for (int nn=0 ; nn < mydata.length ; nn++) {
	    mydata[nn] = Math.cos(nn);
	}

	//Write it
	IHDF5SimpleWriter writer = HDF5Factory.open("out.h5");
	writer.writeDoubleArray("cosines", mydata);
	writer.close();

	//Read it back
	IHDF5SimpleReader reader = HDF5Factory.openForReading("out.h5");
	double[] readdata = reader.readDoubleArray("cosines");
	reader.close();

	//Inspect it
	if(readdata.length != mydata.length) {
	    System.out.println("Array length is not 1000 as expected");
	    System.exit(1);
	}

	for (int nn=0 ; nn < mydata.length ; nn++ ) {
	    if(readdata[nn] != mydata[nn]) {
		System.out.println("Data mismatch at index " + nn);
		System.exit(1);
	    }
	}

	//Happy!
	System.out.println("OK");
	//exit(0);
    }
}
