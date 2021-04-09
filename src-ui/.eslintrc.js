module.exports = {
  root: true,
  env: {
    node: true
  },
  parser: "vue-eslint-parser",
  extends: [
    "plugin:vue/essential",
    "plugin:@typescript-eslint/recommended",
    "eslint:recommended",
    "@vue/typescript/recommended",
    "@vue/prettier",
    "@vue/prettier/@typescript-eslint"
  ],
  parserOptions: {
    ecmaVersion: 2020,
    parser: "@typescript-eslint/parser"
  },
  rules: {
    "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
    "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off",
    "vue/valid-v-slot": "off",
    "@typescript-eslint/camelcase": "off",
    "@typescript-eslint/no-empty-interface": "off",
    "@typescript-eslint/ban-ts-comment": "off"
  }
};
