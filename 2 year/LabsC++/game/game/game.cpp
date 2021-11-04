#include "game.h"

#include "game_library/Level.h"

Game::Game() : BaseGame() {
  LevelManager::getInstance().addLevel<GameLevel>();
}

void Game::preload() {
  LevelManager::getInstance().start(getRenderer());
}

void Game::render() {
  SDL_Renderer *renderer = getRenderer();
  LevelManager::getInstance().render();
  SDL_RenderPresent(renderer);
}

void Game::handleEvents() {
  SDL_Event event;
  while (SDL_PollEvent(&event)) {
    LevelManager::getInstance().onEvents(event);
    onEvents(event);
  }
}
//void Game::onKeyDown(const SDL_Keysym& sym) {
	//if(sym.sym==SDLK_d)
	//if (sym.sym == SDLK_a)
	//if (sym.sym == SDLK_w)

//}



void Game::update(float dt) {
  static constexpr float freq = 120.0f;
  static float time_accumulator = 0.0f;

  float ifreq = 1.0f / freq;
  time_accumulator += dt;

  while (time_accumulator > ifreq) {
    time_accumulator -= ifreq;
    LevelManager::getInstance().update(ifreq);
  }
}

void Game::onQuit() { quit(); }

Game::~Game() {}
