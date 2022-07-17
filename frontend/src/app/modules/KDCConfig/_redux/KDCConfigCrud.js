import { BACKEND_ORIGIN } from "../../../../config";
import axios from "axios";

export const getAllKDCLinguisticDomainConfigs = () => {
  const GET_HEDGE_CONFIGS = BACKEND_ORIGIN + `hedge-algebra/linguistic-domain`;
  return axios.get(GET_HEDGE_CONFIGS, {
    params : {
      appId: "KDC"
    }
  })
}

export const getAllKDCHedgeConfigs = () => {
  const GET_HEDGE_CONFIGS = BACKEND_ORIGIN + `hedge-algebra/config`;
  return axios.get(GET_HEDGE_CONFIGS, {
    params : {
      appId: "KDC"
    }
  })
}

