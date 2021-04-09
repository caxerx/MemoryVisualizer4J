function simpleTypeName(type: string) {
  return type.split(".").splice(-1)[0];
}
function hexMemoryAddress(address: string) {
  return parseInt(address).toString(16);
}
export default {
  simpleTypeName,
  hexMemoryAddress
};
