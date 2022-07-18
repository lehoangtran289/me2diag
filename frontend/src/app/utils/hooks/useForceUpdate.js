import React, {useState} from 'react';

//create your forceUpdate hook
export default function useForceUpdate() {
  const [value, setValue] = useState(0);
  return () => setValue(value => value + 1);
  // An function that increment 👆🏻 the previous state like here
  // is better than directly setting `value + 1`
}