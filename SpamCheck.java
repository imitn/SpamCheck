import java.net.InetAddress;
import java.net.UnknownHostException;

/**使用DNS巧妙地判断垃圾邮件发送者
 * 
 * UTF-8编码
 * @author 吴佳东
 */
public class SpamCheck {
	public static final String BLACKHOLE = "sbl.spamhaus.org";// 实时黑名单列表地址

	/**
	 * 检查一个给定IP地址是否为已知垃圾邮件发送者
	 * 
	 * @param checkedAddress
	 * @return
	 */
	private static boolean isSpammer(String checkedAddress) {
		try {
			InetAddress address = InetAddress.getByName(checkedAddress);
			byte[] ip = address.getAddress();// 返回IP地址的无符号字节数组
			String query = BLACKHOLE;
			// 字节数组是无符号的，在java中值大于127的字节会当做负数，
			// 必须提升为int类型,然后把ip地址逆向拼接到实时黑名单列表地址
			for (byte oneByteOfIp : ip) {
				int unsignedByte = oneByteOfIp < 0 ? oneByteOfIp + 256 : oneByteOfIp;
				query = unsignedByte + "." + query;
			}
			InetAddress.getByName(query);
			return true;// 没有抛出异常，说明在实时黑名单列表查询到了该地址

		} catch (UnknownHostException e) {
			return false;// 实时黑名单列表没有该地址信息
		}
	}

	public static void main(String[] args) {

		if (args.length > 0) {
			for (String address : args) {
				if (isSpammer(address))
					System.out.println(address + " 是垃圾邮件发送者！");
				else
					System.out.println(address + " 不是垃圾邮件发送者！");
			}
		} 
		else {

			String aSpammer = "107.150.166.80";// 垃圾邮件发送者
			String aBaiduAddress = "119.75.218.70";//百度的一个IP地址

			if (isSpammer(aSpammer))
				System.out.println(aSpammer + " 是垃圾邮件发送者！");
			else
				System.out.println(aSpammer + " 不是垃圾邮件发送者！");

			if (isSpammer(aBaiduAddress))
				System.out.println(aBaiduAddress + " 是垃圾邮件发送者！");
			else
				System.out.println(aBaiduAddress + " 不是垃圾邮件发送者！");
		}

	}

}
