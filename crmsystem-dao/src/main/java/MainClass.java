import com.zilong.utils.CreateEntityUtil;
import com.zilong.utils.CreateMapUtil;
import com.zilong.vo.dataVo.Connection;
import com.zilong.vo.purchase.Purchasenote;
import com.zilong.vo.salesVo.Salesorder;
import com.zilong.vo.salesVo.Salesticket;

public class MainClass {
    public static void main(String[] args) {
        CreateMapUtil.init("root",
                "zzl",
                "jdbc:mysql://localhost:3306/ssm_project?characterEncoding=utf-8",
                "com.mysql.jdbc.Driver",
                "com.zilong.dao.salesDao");
        CreateMapUtil.addClass(Salesticket.class);

        CreateMapUtil.write();

        /*CreateEntityUtil.init("root",
                "zzl",
                "jdbc:mysql://localhost:3306/ssm_project?characterEncoding=utf-8",
                "com.mysql.jdbc.Driver",
                "com.zilong.vo.dataVo");
        CreateEntityUtil.addTable("salesticket");
        CreateEntityUtil.useFkEntity = true;
        CreateEntityUtil.usePkEntityList = true;
        CreateEntityUtil.write(true,true,true);*/

        /*Calendar now = Calendar.getInstance();
        System.out.println("年：" + now.get(Calendar.YEAR));
        System.out.println("月：" + (now.get(Calendar.MONTH) + 1));
        System.out.println("日：" + now.get(Calendar.DAY_OF_MONTH));*/
        /*System.out.println("时：" + now.get(Calendar.HOUR_OF_DAY));
        System.out.println("分：" + now.get(Calendar.MINUTE));
        System.out.println("秒：" + now.get(Calendar.SECOND));
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("当前时间：" + sdf.format(d));*/
        //System.out.println("当前时间："+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}
