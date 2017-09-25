package day11;

import java.io.File;

/*批量修改文件名称*/
public class Test4 {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		File file = new File("f:\\abc"); // 设要对f:\\abc路径下的所有的照片重命名

		File[] lf = file.listFiles(); // 以文件的形式返回当前的文件夹里的所有文件

		int num = lf.length; // 获取该文件的文件数

		File[] lfnew = new File[num]; // 新建一个与该文件的文件数一样的File类型数组

		for (int x = 0; x < num; x++) {

			lfnew[x] = new File(file, "2015-4-15-".concat(String.valueOf(x).concat(".JPG"))); // 设置新数组的文件路径

			lf[x].renameTo(lfnew[x]); // 修改原文件夹内的文件名
		}

	}

}
