#pragma once

#include "../framework/framework.h"
#include <map>
#include <string>
#include <vector>
#include "entity.h"

struct Layer {
  std::vector<std::vector<Tile>> map;

  Layer(std::initializer_list<std::vector<Tile>> list) {
    map.reserve(list.size());
    for (auto& line : list) {
      map.push_back(line);
    }
  }

  size_t width() const {
    return map[0].size();
  }

  size_t height() const {
    return map.size();
  }

  const std::vector<Tile>& operator[](int index) const {
    return map[index];
  }
};

class GameLevel : public Level {
public:
  void preload(SDL_Renderer *renderer) override;
  void update(float time);
  //void onEvents(const SDL_Event& event);
 // void onKeyDown(const SDL_Keysym& sym);
  void draw(SDL_Renderer* renderer) override;

private:
  std::vector<Layer> layers;
  player playerBody;
  std::vector<BANANA> bananas;
  std::vector<BLOCK> Blocks;
  std::vector<ghost> GHOST;
  //ghost ghostBody;
  //ghost ghostBody_2;
  //ghost ghostBody_3;

  //move_obj skullBody;

 // entity chickenBody;
  //entity bunnyBody;

  //entity* blocks;
};
