/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bean;

/**
 *
 * @author aketz
 */
public class Image {

    private String path;
    private String name;
    private long size;

    @SuppressWarnings("empty-statement")
    public Image(String path, long size) {
        this.path = path.replace("\\", "/");;
        this.name = this.getNameFromPath(this.path);
        this.size = size;
    }

    private String getNameFromPath(String name) {
        final int lastIndexOfSlash = name.lastIndexOf("/");
        final int lastIndexOfDot = name.lastIndexOf(".");

        if (lastIndexOfSlash != -1) {
            if (lastIndexOfDot != -1) {
                return name.substring(lastIndexOfSlash + 1, lastIndexOfDot);
            }
        } else {
            if (lastIndexOfDot != -1) {
                return name.substring(0, lastIndexOfDot);
            }
        }

        return name;
    }

    public String BrokenDownSize() {
        String broken = "";

        long bytes = this.size;
        long megabytes = bytes / (1024 * 1024);
        long kilobytes = bytes % (1024 * 1024) / 1024;
        bytes = bytes % (1024 * 1024) % 1024;

        if (megabytes > 0) {
            broken += megabytes + "MB";
            broken += " ";
        }
        if (kilobytes > 0) {
            broken += kilobytes + "KB";
            broken += " ";
        }
        if (bytes > 0) {
            broken += bytes + "B";
        }

        return broken;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

}
