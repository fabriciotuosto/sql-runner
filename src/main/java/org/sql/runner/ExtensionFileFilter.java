package org.sql.runner;

import java.io.File;
import java.io.FileFilter;

class ExtensionFileFilter implements FileFilter{

	private final String extension;
	
	public ExtensionFileFilter(String extension) {
		this.extension = Preconditions.checkNotEmpty(extension);
	}
	@Override
	public boolean accept(File pathname) {
		Preconditions.checkNotNull(pathname);
		return pathname.getName().endsWith(extension);
	}

}
