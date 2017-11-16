package com.aaronshaver;

import java.io.File;

public class FileHelpers {
    public boolean isValidPath(String path) {
        File f = new File(path);
        return(f.exists() && !f.isDirectory());
    }
}
