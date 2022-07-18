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

export const saveKDCHedgeConfigs = (data) => {
  const POST_HEDGE_CONFIGS = BACKEND_ORIGIN + `hedge-algebra/config`;
  return axios.put(POST_HEDGE_CONFIGS, {
    app_id: "KDC",
    neutral_theta : data.neutral_theta,
    configs : {
      LITTLE: data.LITTLE,
      POSSIBLE: data.POSSIBLE,
      MORE: data.MORE,
      VERY: data.VERY
    }
  })
}


