//ExtFilter.java

package view;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class ExtFilter extends FileFilter {
  private String extensie;

  public ExtFilter (String ext) {
    extensie = ext.toLowerCase();
  }

  public boolean accept (File file) {
    return file.getPath().toLowerCase().endsWith(extensie);
  }

  public String getDescription () {
    return extensie + " files";
  }
}