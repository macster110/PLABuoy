package criodaq;

import Acquisition.AcquisitionControl;
import Acquisition.DaqSystem;
import Acquisition.DaqSystemInterface;
import PamController.PamguardVersionInfo;

public class CRioPluginInfo implements DaqSystemInterface {

	private String jarFile;

	@Override
	public String getDefaultName() {
		return "CRio Network DAQ";
	}

	@Override
	public String getHelpSetName() {
		return null;
	}

	@Override
	public void setJarFile(String jarFile) {
		this.jarFile = jarFile;
	}

	@Override
	public String getJarFile() {
		return jarFile;
	}

	@Override
	public String getDeveloperName() {
		return "Douglas Gillespie";
	}

	@Override
	public String getContactEmail() {
		return "dg50@st-andrews.ac.uk";
	}

	@Override
	public String getVersion() {
		return "2.0";
	}

	@Override
	public String getPamVerDevelopedOn() {
		return PamguardVersionInfo.version;
	}

	@Override
	public String getPamVerTestedOn() {
		return PamguardVersionInfo.version;
	}

	@Override
	public String getAboutText() {
		return null;
	}

	@Override
	public DaqSystem createDAQControl(AcquisitionControl acObject) {
		return new NINetworkDaq(acObject);
	}

}
