package org.hac.drc.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileSplitter {

	private BufferedReader reader;
	private int splitlen;
	private String fileName;
	private int totalLines;
	
	public FileSplitter(){
		
	}
	
	public FileSplitter(String fileName, int splitLength){
		try {
			this.reader = Files.newBufferedReader(Paths.get(fileName));
			this.fileName = fileName;
			this.splitlen = splitLength;
			this.totalLines = (int) Files.lines(Paths.get(fileName)).count();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> split() throws IOException {
		List<String> fileNames = new ArrayList<String>();
		int fileCount = 1;
		for(int i = 0; i < totalLines;){
			i = i+this.splitlen;
			BufferedWriter bw = null;
			new File(fileName + "_" + fileCount).createNewFile();
			bw = Files.newBufferedWriter(Paths.get(fileName + "_" + fileCount), StandardCharsets.UTF_8,StandardOpenOption.WRITE);
			for(int j = 1; j<= this.splitlen; j++){
				String line = reader.readLine();
				if(line != null && !line.isEmpty()){
					bw.write(line);
					bw.newLine();
				}
			}
			bw.flush();
			bw.close();
			fileNames.add(fileName + "_" + fileCount);
			fileCount++;
		}
		reader.close();
		return fileNames;
	}
	
	public List<List<String>> splitFile(List<String> lines){
		
		int numberOfParts = 7;
		 List<List<String>> chunkList = new ArrayList<>();
			  int numberOfLines = lines.size();
			  
			  int blockSize = (int) Math.ceil(numberOfLines/numberOfParts);
			  
			  int i = 0;
			  int chunkListIndex = 0;
			  
			  for(; i<(numberOfParts-1)*blockSize; i+=blockSize){
				  int toIndex = i+blockSize;
				  chunkList.add(chunkListIndex++, new ArrayList<>(lines.subList(i, toIndex)));
				  
			  }
			  chunkList.add(chunkListIndex, new ArrayList<>(lines.subList(i, (int) numberOfLines)));
		  
		 
		 return chunkList;	 
		 
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public int getSplitlen() {
		return splitlen;
	}

	public void setSplitlen(int splitlen) {
		this.splitlen = splitlen;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}
	
	
	
}
