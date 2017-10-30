package hud;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import game.Nation;
import graphics.Text;
import gui.guiButton;
import main.Main;

public class Diplomacy 
{
	float x, y;
	float width, height;
	private Nation nation;
	private guiButton declareWar;
	private guiButton declarePeace;
	private guiButton improveRelations;
	private guiButton sendGift;
	private guiButton alliance;
	private Text relations;
	private Text status;
	private Text name;
	
	public Nation getNation()
	{
		return nation;
	}
	
	public void render()
	{
		//draw box
		NanoVG.nvgBeginPath(Main.vg);
		NVGColor color = NVGColor.create();
		color.a(1.0f);
		color.r(1.0f);
		color.g(1.0f);
		color.b(1.0f);
		NanoVG.nvgRect(Main.vg, x, y, width, height);
		NanoVG.nvgFillColor(Main.vg, color);
		NanoVG.nvgFill(Main.vg);
		name.render();
		status.render();
		relations.render();
		declareWar.render();
		declarePeace.render();
		improveRelations.render();
		sendGift.render();
		alliance.render();
	}
	
	public void update()
	{
		declareWar.update();
		if(declareWar.getActivated() && nation.getDiplomacy(Main.level.player) == "Peace")
		{
			nation.setDiplomacy(Main.level.player, "War");
			Main.level.getPlayerNation().setDiplomacy(nation, "War");
		}
		declarePeace.update();
		if(declarePeace.getActivated() && nation.getDiplomacy(Main.level.player) == "War")
		{
			nation.setDiplomacy(Main.level.player, "Peace");
			Main.level.getPlayerNation().setDiplomacy(nation, "Peace");
		}
		improveRelations.update();
		if(improveRelations.getActivated())
		{
			nation.changeRelations(Main.level.player, 10);
		}
		sendGift.update();
		if(sendGift.getActivated() && Main.level.player.getMoney() >= 1)
		{
			Main.level.player.changeMoney(-1);
			nation.changeMoney(1);
			nation.changeRelations(Main.level.player, 25);
		}
		alliance.update();
		if(alliance.getActivated() && nation.getRelations(Main.level.player) >= 100)
		{
			nation.setDiplomacy(Main.level.player, "Alliance");
			Main.level.player.setDiplomacy(nation, "Alliance");
		}
		name.setText(nation.getName());
		relations.setText(Integer.toString(nation.getRelations(Main.level.player)));
		status.setText(nation.getDiplomacy(Main.level.player));
	}
	
	public Diplomacy(float x, float  y, float width, float height, Nation nation)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.nation = nation;
		declareWar = new guiButton(x, y + 60, "Declare war");
		declarePeace = new guiButton(x, y + 80, "Declare peace");
		improveRelations = new guiButton(x, y + 100, "Improve relations");
		sendGift = new guiButton(x, y + 120, "Send gift");
		alliance = new guiButton(x, y + 140, "Alliance");
		relations = new Text(x, y, "");
		status = new Text(x, y + 20, "");
		name = new Text(x, y + 40, "");
	}
}
