package org.hac.drc.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

	private static final Pattern RENAME_PATTERN = Pattern.compile("_\\d$");
	
	public static  File moveFile(File file, File newDirectory){
		String fileName = file.getName();
		File movedFile = new File(newDirectory, fileName);
		
		while(movedFile.exists()){
			String newFileName;
			Matcher matcher = RENAME_PATTERN.matcher(movedFile.getName());
			if(matcher.find()){
				String fileNameWithoutSuffix = movedFile.getName().substring(0,matcher.start());
				int version = Integer.parseInt(matcher.group().substring(1));
				
				newFileName = fileNameWithoutSuffix+"_" +(version+1);
			}else{
				newFileName = movedFile.getName()+"_1";
			}
			movedFile = new File(newDirectory, newFileName);
		}
		file.renameTo(movedFile);
		return movedFile;
	}
	
	public static void deleteFilesFromDir(String rootDirPath){
		
		Path rootPath = Paths.get(rootDirPath);
		
		try{
			Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS).sorted(Comparator.reverseOrder()).map(Path :: toFile)
			.forEach(f -> {
				if(!f.getAbsolutePath().equals(rootDirPath)){
					f.delete();
				}
			});
		}catch(IOException e){
			
		}
	}
	
	public static File appendTimeStampToFileName(File file){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss.S");
		File fileWithTimeStamp = new File(file.getAbsolutePath()+"_"+dateFormat.format(new Date()));
		file.renameTo(fileWithTimeStamp);
		return fileWithTimeStamp;
		
	}
	/*public static synchronized void writeToErrorFile(String source, String errorRecord){
		
		File errorFile = new File(ErrorFilesLocation.getFilePath(source));
		
		if(!errorFile.exists()){
			try{
				errorFile.createNewFile();
			}catch(IOException e){
				
			} 
		}
		
		try(PrintStream ps = new PrintStream(new FileOutputStream(errorFile,true))){
			ps.println(errorRecord);
		}catch(FileNotFoundException e){
			
		}
	}*/
	
	public static String readFile(String path) throws IOException{
		StringBuilder sb = new StringBuilder();
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			String sCurrentLine="";
			while((sCurrentLine = br.readLine()) != null){
				sb.append(sCurrentLine);
			}
		}
		return sb.toString();
	}
}
