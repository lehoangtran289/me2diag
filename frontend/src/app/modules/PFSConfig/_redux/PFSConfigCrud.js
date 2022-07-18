import { BACKEND_ORIGIN } from "../../../../config";
import axios from "axios";

export const getAllPFSLinguisticDomainConfigs = () => {
  const GET_HEDGE_CONFIGS = BACKEND_ORIGIN + `hedge-algebra/linguistic-domain`;
  return axios.get(GET_HEDGE_CONFIGS, {
    params : {
      appId: "PFS"
    }
  })
}

export const getAllPFSHedgeConfigs = () => {
  const GET_HEDGE_CONFIGS = BACKEND_ORIGIN + `hedge-algebra/config`;
  return axios.get(GET_HEDGE_CONFIGS, {
    params : {
      appId: "PFS"
    }
  })
}

export const savePFSHedgeConfigs = (data) => {
  const POST_HEDGE_CONFIGS = BACKEND_ORIGIN + `hedge-algebra/config`;
  return axios.put(POST_HEDGE_CONFIGS, {
    app_id: "PFS",
    neutral_theta : data.neutral_theta,
    configs : {
      SLIGHTLY: data.SLIGHTLY,
      VERY: data.VERY
    }
  })
}


