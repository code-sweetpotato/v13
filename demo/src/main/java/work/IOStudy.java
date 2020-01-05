package work;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOStudy {
    private File getFile() throws IOException {
        //以文件名创建文件变量
        File filename = new File("filename");
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        //创建后的文件会生成默认的项目文件夹下，无需指定文件路径
        try {
            filename.createNewFile();
            fileWriter = new FileWriter(filename);
            //数据库中只包含文字信息，建议使用bufferedWriter写入文件，更快速。
            bufferedWriter = new BufferedWriter(fileWriter);
            List<TUser> userList = new ArrayList<>();//假设该集合储存的是从数据库取得的用户信息
            if(CollectionUtils.isNotEmpty(userList)){
                for (TUser tUser : userList) {
                    bufferedWriter.write(tUser.toString());//将toString方法重写成需求要求文件的格式
                    bufferedWriter.newLine();//换行
                }
                //一定调用flush方法，不然用户信息不会写入文件中,文件写入完成后，关流
                fileWriter.flush();
            }
            InputStream is = new FileInputStream(filename);//文件中得到流
            String s = "将数据封装成字符串";
            byte[] bytes = s.getBytes();
            InputStream is2 = new ByteArrayInputStream(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }finally {
            IOUtils.closeQuietly(bufferedWriter);
            IOUtils.closeQuietly(fileWriter);
            filename.delete();//只有流关闭后，才能将文件删除
        }
        return null;
    }
}
