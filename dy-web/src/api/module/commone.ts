import Http from "../http";

export const tokens = function (params: object) {
  return Http.get("/auth/token", params);
};

export const searchs = function (params: object) {
  return Http.get("/parser/video/search", params);
};

export const pages = function (params: object) {
  return Http.get("/man/comment/page", params);
};
