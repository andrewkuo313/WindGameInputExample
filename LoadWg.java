

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.Map;
import java.util.stream.Collectors;
import test123.wg456.GamePOJO;
import test123.wg456.ItemPOJO;
import test123.wg456.TaskPOJO;

public class LoadWg
{

	public static void main(String[] args)
	{
		
		/*
		 * 每個 *.wg 檔代表不同的大地遊戲
		 * 編輯器產生的 *.wg 檔內依序會有三個不同類別大物件
		 * POJO 物件用途請參考各類別所附說明
		 * 
		 * 
		 * 1. Map<String, ItemPOJO> 物件
		 * key (String) 所對應的是道具(Item) 的 uid，用於辨識道具
		 * 
		 * 2. Map<String, TaskPOJO> 物件
		 * key (String) 所對應的是任務(Task) 的 uid，用於辨識任務 
		 * 
		 * 3. GamePOJO 物件
		 * 
		 * 將 HackedObjectInputStream的套件名稱改成POJO.java對應的套件名稱即可
		 */
		
		File file = new File("D:/MapleStory.wg");
		try
		(
				FileInputStream fileIn = new FileInputStream(file);
				HackedObjectInputStream objectIn = new HackedObjectInputStream(fileIn)
		)
		{
		
		// Item
		@SuppressWarnings("unchecked")
		Map<String, ItemPOJO> items = (Map<String, ItemPOJO>) objectIn.readObject();
		
		// Task
		@SuppressWarnings("unchecked")
		Map<String, TaskPOJO> tasks = (Map<String, TaskPOJO>) objectIn.readObject();
		
		// Game
		GamePOJO gamePOJO = (GamePOJO) objectIn.readObject();
		
		//測試輸出
		tasks.values().forEach((c) ->
		{
			System.out.println("任務名稱： " + c.name);
			System.out.println("緯度： " + c.posLat);
			System.out.println("經度： " + c.posLng);
		});
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static class HackedObjectInputStream extends ObjectInputStream
	{

	    public HackedObjectInputStream(InputStream in) throws IOException
	    {
	        super(in);
	    }

	    @Override
	    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
	        ObjectStreamClass resultClassDescriptor = super.readClassDescriptor();

	        if (resultClassDescriptor.getName().equals("com.gmail.andrewchouhs.model.GamePOJO"))
	            resultClassDescriptor = ObjectStreamClass.lookup(test123.wg456.GamePOJO.class);
	        if (resultClassDescriptor.getName().equals("com.gmail.andrewchouhs.model.ItemPOJO"))
	            resultClassDescriptor = ObjectStreamClass.lookup(test123.wg456.ItemPOJO.class);
	        if (resultClassDescriptor.getName().equals("com.gmail.andrewchouhs.model.TaskPOJO"))
	            resultClassDescriptor = ObjectStreamClass.lookup(test123.wg456.TaskPOJO.class);
	        if (resultClassDescriptor.getName().equals("com.gmail.andrewchouhs.model.TaskPOJO$Condition"))
	            resultClassDescriptor = ObjectStreamClass.lookup(test123.wg456.TaskPOJO.Condition.class);

	        return resultClassDescriptor;
	    }
	}
}
