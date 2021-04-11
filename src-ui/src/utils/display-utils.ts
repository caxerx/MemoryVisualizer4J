function simpleTypeName(type: string): string {
  type = type ?? "";
  return type.split(".").splice(-1)[0];
}
function hexMemoryAddress(address: string): string {
  address = address ?? "0";
  return parseInt(address).toString(16);
}
export default {
  simpleTypeName,
  hexMemoryAddress
};
