#include "level.h"
#include "../find_player.h"
#include <iostream>
#include <SDL.h>

const Tile gray{"Gray"};
const Tile block{ "block" };
const Tile terr{"Gray Terrain"};
const Tile bann{"Bananas"};
const Tile player_{"player"};
const Tile chicken_{ "chicken" };
const Tile ghost_{ "ghost" };
const Tile skull_{ "skull" };
const Tile bunny_{ "bunny" };

int pl_w;
int pl_h;
int g_w;
int g_h;
int bl_w;
int bl_h;
int edge_w;
int edge_h;

// clang-format off
const Layer background({
  std::vector<Tile>({ terr, terr, terr, terr, terr, terr, terr, terr, terr, terr }),
  std::vector<Tile>({ terr, gray, gray, gray, gray, gray, gray, gray, gray, terr }),
  std::vector<Tile>({ terr, gray, gray, gray, gray, gray, gray, gray, gray, terr }),
  std::vector<Tile>({ terr, gray, gray, gray, gray, gray, gray, gray, gray, terr }),
  std::vector<Tile>({ terr, gray, gray, gray, gray, gray, gray, gray, gray, terr }),
  std::vector<Tile>({ terr, gray, gray, gray, gray, gray, gray, gray, gray, terr }),
  std::vector<Tile>({ terr, gray, gray, gray, gray, gray, gray, gray, gray, terr }),
  std::vector<Tile>({ terr, gray, gray, gray, gray, gray, gray, gray, gray, terr }),
  std::vector<Tile>({ terr, gray, gray, gray, gray, gray, gray, gray, gray, terr }),
  std::vector<Tile>({ terr, terr, terr, terr, terr, terr, terr, terr, terr, terr })
});
				

char blocks[] = { 0,1,0,0,0,1,0,0,0,0,1,0,0,0,1,0,
				  0,1,0,1,0,1,1,0,0,1,1,0,1,0,1,0,
				  0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				  1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,
				  0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,
				  0,1,1,1,0,1,0,0,0,0,1,0,1,1,1,0,
				  0,0,1,0,0,1,0,1,1,0,1,0,0,1,0,0,
				  0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				  0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,
				  0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,
				  0,0,1,0,0,0,1,1,1,1,1,0,0,1,0,0,
				  0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,
				  0,1,0,1,0,0,1,0,1,0,0,0,1,0,1,0,
				  0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,0,
				  0,1,0,0,1,0,0,0,0,0,0,1,0,0,1,0,
				  1,0,0,1,0,0,0,1,1,1,0,0,1,0,0,1,};
// clang-format on
std::vector<float> pos_gh;
void GameLevel::preload(SDL_Renderer *renderer) {
  TextureManager::getInstance()->load(renderer, "Gray",
                                      "resources/images/gray.png", 64, 64);
  TextureManager::getInstance()->load(
      renderer, "Gray Terrain", "resources/images/gray_terrain.png", 64, 64);

  TextureManager::getInstance()->load(
	  renderer, "block", "resources/images/block.png", 32, 32);

  TextureManager::getInstance()->load(
      renderer, "player", "resources/images/Player.png", 26, 26);
  TextureManager::getInstance()->load(renderer, "Bananas",
                                      "resources/images/Bananas.png", 32, 32);

  TextureManager::getInstance()->load(
	  renderer, "ghost", "resources/images/Idle (44x30).png", 314, 30);


  //int ghost_number = 3;
  pl_w = TextureManager::getInstance()->get(player_.texture).getWidth();
  pl_h = TextureManager::getInstance()->get(player_.texture).getHeight();
  bl_w = TextureManager::getInstance()->get(block.texture).getWidth();
  bl_h = TextureManager::getInstance()->get(block.texture).getHeight();
  edge_w = TextureManager::getInstance()->get(terr.texture).getWidth();
  edge_h = TextureManager::getInstance()->get(terr.texture).getHeight();
  g_w= TextureManager::getInstance()->get(ghost_.texture).getWidth()/10;
  g_h= TextureManager::getInstance()->get(ghost_.texture).getHeight();
  for (int i = 0; i < 16; i++)
	  for (int j = 0; j < 16; j++)
	  {
		  if (blocks[i * 16 + j] == 1)
		  {
			  BLOCK def(edge_w + j * bl_w + bl_w / 2, edge_h + i * bl_h + bl_h / 2);
			  def.set_text(block);
			  Blocks.push_back(def);
		  }
		  else 
		  {
			  BANANA def(edge_w + j * bl_w + bl_w / 2, edge_h + i * bl_h + bl_h / 2);
			  def.set_text(bann);
			  bananas.push_back(def);
		  }
	  }

  playerBody.set_text(player_);
  playerBody.set_place(edge_w+pl_w/2, edge_h+pl_h/2);//64*10 - размер окна
  //playerBody.set_frames(11);
  //playerBody.set_time(0.04);
  pos_gh.push_back((background.width() - 1) * edge_w - bl_w / 2);
  pos_gh.push_back((background.height() - 1) * edge_h - bl_h * 8.5);
  pos_gh.push_back((background.width() - 1) * edge_w - bl_w / 2);
  pos_gh.push_back((background.height() - 1) * edge_h - bl_h * 11.5);
  pos_gh.push_back((background.width() - 1) * edge_w - bl_w * 15.5);
  pos_gh.push_back((background.height() - 1) * edge_h - bl_h * 11.5);
  for (int i = 0; i < pos_gh.size(); i+=2)
  {
	  ghost def_;
	  def_.set_place(pos_gh[i], pos_gh[i + 1]);
	  def_.set_text(g_w,g_h);
	  GHOST.push_back(def_);
  }

 GHOST[0].set_velocity(-40, 0);
 GHOST[1].set_velocity(0, 40);
 GHOST[2].set_velocity(0, 40);
}
void GameLevel::update(float time) {
	SDL_Event event;
	SDL_PollEvent(&event);
	switch (event.type) {
	case SDL_KEYDOWN:
		if (event.button.button == SDL_SCANCODE_A) playerBody.set_velocity(-60.0f, 0.0f);
		if (event.button.button == SDL_SCANCODE_D)playerBody.set_velocity(60.0f, 0.0f);
		if (event.button.button == SDL_SCANCODE_W) playerBody.set_velocity(0.0f, -60.0f);
		if (event.button.button == SDL_SCANCODE_S) playerBody.set_velocity(0.0f, 60.0f);
		break;
	case SDL_KEYUP:
		if (event.button.button == SDL_SCANCODE_A) playerBody.set_velocity(0.0f, 0.0f);
		if (event.button.button == SDL_SCANCODE_D)playerBody.set_velocity(0.0f, 0.0f);
		if (event.button.button == SDL_SCANCODE_W) playerBody.set_velocity(0.0f, 0.0f);
		if (event.button.button == SDL_SCANCODE_S) playerBody.set_velocity(0.0f, 0.0f);

		break;
	}
	playerBody.check_collision(edge_w, edge_h);

	for (int i = 0; i < GHOST.size(); i++)
	{
	GHOST[i].check_collision(edge_w, edge_h);
	}

		for (int i = 0; i < Blocks.size(); i++)
		{
			playerBody.check_collision(Blocks[i]);
			for (int j = 0; j < GHOST.size(); j++)
			{
				GHOST[j].check_collision(Blocks[i]);
			}
		}
	for (int i = 0; i < bananas.size(); i++)
		playerBody.check_collision(bananas[i]);
		/*if (((playerBody.get_right() - 2 > bananas[i].get_left()) && (playerBody.get_right() + 2 < bananas[i].get_right())
			|| (playerBody.get_left() - 2 > bananas[i].get_left()) && (playerBody.get_left() + 2 < bananas[i].get_right()))
			&& ((playerBody.get_top() - 2 > bananas[i].get_top()) && (playerBody.get_top() + 2 < bananas[i].get_bot())
				|| (playerBody.get_bot() - 2 > bananas[i].get_top()) && (playerBody.get_bot() + 2 < bananas[i].get_bot())))
		{ playerBody.get_banana(); bananas[i].drawy=0; }*/
	
	playerBody.move(time);

	for (int i = 0; i < GHOST.size(); i++)
	{
		GHOST[i].move(time);
		playerBody.check_collision(GHOST[i]);
	}
}
void GameLevel::draw(SDL_Renderer *renderer) {
	int banana_count=0;
  for (int i = 0; i < background.width(); ++i)
    for (int j = 0; j < background.height(); ++j) {
      Texture texture =
          TextureManager::getInstance()->get(background[j][i].texture);
      SDL_Rect src_rect{0, 0, texture.getWidth(), texture.getHeight()};
      SDL_Rect dst_rect{i *texture.getWidth(), j * texture.getHeight(),
                        texture.getWidth(), texture.getHeight()};
      SDL_RenderCopy(renderer, texture, &src_rect, &dst_rect);
    }

  for (int i = 0; i < Blocks.size(); i++)
  {
	  Texture texture =
		  TextureManager::getInstance()->get(block.texture);
	  SDL_Rect src_rect{ 0, 0, texture.getWidth(), texture.getHeight() };
	  SDL_Rect dst_rect{ Blocks[i].get_left(), Blocks[i].get_top(),//64 - размер рамки в пикс
						texture.getWidth(), texture.getHeight() };
	  SDL_RenderCopy(renderer, texture, &src_rect, &dst_rect);
  }

  for(int i=0;i<bananas.size();i++)
	  if (bananas[i].drawy)
	  {
		  Texture texture =
			  TextureManager::getInstance()->get(bann.texture);
		  SDL_Rect src_rect{ 0, 0, texture.getWidth(), texture.getHeight() };
		  SDL_Rect dst_rect{ bananas[i].get_left(),bananas[i].get_top(),//64 - размер рамки в пикс
							texture.getWidth(), texture.getHeight() };
		  SDL_RenderCopy(renderer, texture, &src_rect, &dst_rect);
		  banana_count += 1;
	  }

	  if (banana_count == 0) quit();
	  if(playerBody.dead()) quit();
  playerBody.draw_(renderer, playerBody.x(), playerBody.y(), player_.texture);
  for (int i=0; i < GHOST.size(); i++)
	  GHOST[i].draw_(renderer, GHOST[i].x(), GHOST[i].y(), ghost_.texture);
  Level::draw(renderer);;
}
