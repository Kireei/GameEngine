package map;



import image.Image;

public class Map {
		
	private int[] map;
	
	public Map(){
		
	}
	
	public int[] readMap(Image image){
		map = new int[image.getH() * image.getW()];
		for(int y = 0; y < image.getH(); y++){
			for(int x = 0; x < image.getW(); x++){
				if(image.getP()[x + y * image.getH()] == 0xff000000){
					map[x + y * image.getH()] = 0;
				}
				if(image.getP()[x + y * image.getH()] == 0xffffffff){
					map[x + y * image.getH()] = 1;
				}
			}
		}
		return map;
	}
}
