//#include "../game.h"
#include <SDL.h>
//#include "../game_library/Level.h"
#include "../framework/framework.h"
#include <SDL.h>

//#include "game.h"

class Obj
{
private:
	//std::string texture_name_;
	int frames_number_;
	float frames_time_;
	float dt_;
public:
	Obj(int frames = 0,float time=0,float dt=0) {
		frames_number_ = frames;
		frames_time_ = time;
		dt_ = dt;
	}
	void set_frames(int frames) {
		frames_number_ = frames;
	}
	void set_time(int time) {
		frames_number_ = time;
	}
	void update_(float dt) {
		dt_ += dt;
	}
	~Obj() {}


	void draw_(SDL_Renderer* renderer, float x, float y, std::string texture_name_) {
		if (dt_ < 0)dt_ = 0;
			Texture texture = TextureManager::getInstance()->get(texture_name_);
			SDL_Rect src{ 0, 0, texture.getHeight(), texture.getHeight() };
			SDL_Rect dst{ x - 1 * texture.getHeight() / 2 ,
						 y - 1 * texture.getHeight() / 2 ,
						 texture.getHeight() ,
						 texture.getHeight() };
			
				SDL_RenderCopy(renderer, texture, &src, &dst);
		}
};
