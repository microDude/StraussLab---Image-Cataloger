/**
 * Company:	com.gregorygutshall
 * Author:	gutshall
 *
 *			Copyright (c) com.gregorygutshall
 *			All rights reserved. 
 */
package edu.oregonstate.forestry.test;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Test the {@link WatchService} and moving files.
 * 
 * @author gutshall
 */
public class CFileIOTest extends TestCase {

	public void testWatchDirectory() throws Exception {
		WatchService watcher = FileSystems.getDefault().newWatchService();
		Map<WatchKey,Path> keys = new HashMap<>();
		
		// Register the path
		Path pickUpDir = Paths.get("./test/pickupDir");
		WatchKey keyPickUp = pickUpDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
		keys.put(keyPickUp, pickUpDir);
		
		// Test for 60 seconds
		System.out.println("Start period to look for files.");
		long startTime = new Date().getTime();
		while(Math.abs(startTime - new Date().getTime()) < 60000 ) {
			
			// wait for key to be signalled
            WatchKey key = watcher.take();

            // Get the directory associated with this key
            Path dir = keys.get(key);
            
            // Context for directory entry event is the file name of entry
            WatchEvent<?> event = key.pollEvents().get(0);

            Path name = (Path) event.context();
            Path child = dir.resolve(name);

            // print out event
            System.out.format("%s: %s\n", event.kind().name(), child);
            
            // Move File and Rename
            Path newDirectory = Paths.get("./test/dropOffDir");
            Files.move(child, newDirectory.resolve("XYZ_" + new Date().getTime() + ".txt"), StandardCopyOption.REPLACE_EXISTING);

            // reset key
            key.reset();

		}
		
		System.out.println("Finished");
		
	}
	
}
