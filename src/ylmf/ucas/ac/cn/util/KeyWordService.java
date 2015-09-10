package ylmf.ucas.ac.cn.util;

import java.util.Random;

public class KeyWordService {
	public static final String BASE_CHARATERS = new StringBuilder()
			.append("\u5f53\u8718\u86db\u7f51\u65e0\u60c5\u5730\u67e5\u5c01")
			.append("\u4e86\u6211\u7684\u7089\u53f0\u5f53\u7070\u70ec\u7684")
			.append("\u4f59\u70df\u53f9\u606f\u7740\u8d2b\u56f0\u7684\u60b2")
			.append("\u54c0\u6211\u4f9d\u7136\u56fa\u6267\u5730\u94fa\u5e73")
			.append("\u5931\u671b\u7684\u7070\u70ec\u7528\u7f8e\u4e3d\u7684")
			.append("\u96ea\u82b1\u5199\u4e0b\u76f8\u4fe1\u672a\u6765\u5f53")
			.append("\u6211\u7684\u7d2b\u8461\u8404\u5316\u4e3a\u6df1\u79cb")
			.append("\u7684\u9732\u6c34\u5f53\u6211\u7684\u9c9c\u82b1\u4f9d")
			.append("\u504e\u5728\u522b\u4eba\u7684\u60c5\u6000\u6211\u4f9d")
			.append("\u7136\u56fa\u6267\u5730\u7528\u51dd\u971c\u7684\u67af")
			.append("\u85e4\u5728\u51c4\u51c9\u7684\u5927\u5730\u4e0a\u5199")
			.append("\u4e0b\u76f8\u4fe1\u672a\u6765\u6211\u8981\u7528\u624b")
			.append("\u6307\u90a3\u6d8c\u5411\u5929\u8fb9\u7684\u6392\u6d6a")
			.append("\u6211\u8981\u7528\u624b\u638c\u90a3\u6258\u4f4f\u592a")
			.append("\u9633\u7684\u5927\u6d77\u6447\u66f3\u7740\u66d9\u5149")
			.append("\u90a3\u679d\u6e29\u6696\u6f02\u4eae\u7684\u7b14\u6746")
			.append("\u7528\u5b69\u5b50\u7684\u7b14\u4f53\u5199\u4e0b\u76f8")
			.append("\u4fe1\u672a\u6765\u6211\u4e4b\u6240\u4ee5\u575a\u5b9a")
			.append("\u5730\u76f8\u4fe1\u672a\u6765\u662f\u6211\u76f8\u4fe1")
			.append("\u672a\u6765\u4eba\u4eec\u7684\u773c\u775b\u2014\u2014")
			.append("\u5979\u6709\u62e8\u5f00\u5386\u53f2\u98ce\u5c18\u7684")
			.append("\u776b\u6bdb\u5979\u6709\u770b\u900f\u5c81\u6708\u7bc7")
			.append("\u7ae0\u7684\u77b3\u5b54\u4e0d\u7ba1\u4eba\u4eec\u5bf9")
			.append("\u4e8e\u6211\u4eec\u8150\u70c2\u7684\u76ae\u8089\u90a3")
			.append("\u4e9b\u8ff7\u9014\u7684\u60c6\u6005\u5931\u8d25\u7684")
			.append("\u82e6\u75db\u662f\u5bc4\u4e88\u611f\u52a8\u7684\u70ed")
			.append("\u6cea\u6df1\u5207\u7684\u540c\u60c5\u8fd8\u662f\u7ed9")
			.append("\u4ee5\u8f7b\u8511\u7684\u5fae\u7b11\u8f9b\u8fa3\u7684")
			.append("\u5632\u8bbd\u6211\u575a\u4fe1\u4eba\u4eec\u5bf9\u4e8e")
			.append("\u6211\u4eec\u7684\u810a\u9aa8\u90a3\u65e0\u6570\u6b21")
			.append("\u7684\u63a2\u7d22\u8ff7\u9014\u5931\u8d25\u548c\u6210")
			.append("\u529f\u4e00\u5b9a\u4f1a\u7ed9\u4e88\u70ed\u60c5\u5ba2")
			.append("\u89c2\u516c\u6b63\u7684\u8bc4\u5b9a\u662f\u7684\u6211")
			.append("\u7126\u6025\u5730\u7b49\u5f85\u7740\u4ed6\u4eec\u7684")
			.append("\u8bc4\u5b9a").toString();

	public static String getKeyWords(int keywords_length) {
		Random random = new Random();
		int keywords_size = random.nextInt(keywords_length) + 1;
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < keywords_size; i++) {
			stringBuilder.append(KeyWordService.BASE_CHARATERS.charAt(random
					.nextInt(KeyWordService.BASE_CHARATERS.length())));
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		for (int i = 0; i < BASE_CHARATERS.length() - 20; i += 20) {
			for (int j = i; j < i + 20; j++)
				System.out.print(BASE_CHARATERS.charAt(j));
			System.out.println();
		}
	}
}
