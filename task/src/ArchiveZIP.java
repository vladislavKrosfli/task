import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ArchiveZIP implements Archive {
    @Override
    public void unarchive(String input) {
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(input))) {

            ZipEntry entry;
            String name;

            while((entry = zin.getNextEntry()) != null){
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream("unZIP_" + name);

                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }

                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void archive(String output) {
        String[] str = output.split("\\.");
        String name = str[0];

        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(name + ".zip"));
            FileInputStream fis = new FileInputStream(output)) {

            ZipEntry entry = new ZipEntry(output);
            zout.putNextEntry(entry);

            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
