package de.pellepelster.myadmin.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import de.pellepelster.myadmin.server.util.TempFileStore;

@Controller()
@RequestMapping("/fileupload")
public class FileUploadController
{
	@Autowired
	private TempFileStore tempFileStore;

	public void setTempFileStore(TempFileStore tempFileStore)
	{
		this.tempFileStore = tempFileStore;
	}

	@RequestMapping(value = "formUpload", method = RequestMethod.POST)
	@ResponseBody
	public String formUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file)
	{
		try
		{
			return this.tempFileStore.storeTempFile(file.getBytes());
		}
		catch (Exception e)
		{
			return "error";
		}
	}

}