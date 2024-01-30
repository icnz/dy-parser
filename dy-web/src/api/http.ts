import axios, {
  AxiosRequestHeaders,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from "axios";
import { useUserStore } from "../store/user";
import { layer } from "@layui/layui-vue";
import router from "../router";

type TAxiosOption = {
  timeout: number;
  baseURL: string;
  withCredentials: boolean;
};

const config: TAxiosOption = {
  timeout: 5000,
  baseURL: import.meta.env.VITE_APP_BASE_API,
  withCredentials: true,
};

class Http {
  service;
  constructor(config: TAxiosOption) {
    this.service = axios.create(config);

    /* 请求拦截 */
    this.service.interceptors.request.use(
      (config: InternalAxiosRequestConfig) => {
        const userInfoStore = useUserStore();
        if (userInfoStore.token) {
          (config.headers as AxiosRequestHeaders).Authorization = ("Bearer " +
            userInfoStore.token) as string;
        }
        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );

    /* 响应拦截 */
    this.service.interceptors.response.use(
      (response: AxiosResponse<any>) => {
        switch (response.data.code) {
          case 200:
            return response.data;
          case 500:
            return response.data;
          case 701:
            layer.confirm("会话超时, 请重新登录", {
              icon: 2,
              yes: function () {
                router.push("/login");
                layer.closeAll();
              },
            });
            return response.data;
          default:
            break;
        }
      },
      (error) => {
        return Promise.reject(error);
      }
    );
  }

  /* GET 方法 */
  get<T>(url: string, params?: object, _object = {}): Promise<any> {
    return this.service.get(url, { params, ..._object });
  }
  /* POST 方法 */
  post<T>(url: string, params?: object, _object = {}): Promise<any> {
    return this.service.post(url, params, _object);
  }
  /* PUT 方法 */
  put<T>(url: string, params?: object, _object = {}): Promise<any> {
    return this.service.put(url, params, _object);
  }
  /* DELETE 方法 */
  delete<T>(url: string, params?: any, _object = {}): Promise<any> {
    return this.service.delete(url, { params, ..._object });
  }
}

export default new Http(config);
