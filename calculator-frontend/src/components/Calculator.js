import React, { useState } from "react";
import axios from "axios";
import "../styles/Calculator.css";

const Calculator = () => {
  const [a, setA] = useState("");
  const [b, setB] = useState("");
  const [operation, setOperation] = useState("add");
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);

  const handleCalculate = async () => {
    try {
      setError(null); // Limpa erros anteriores
      const response = await axios.get(
        `http://localhost:8080/api/${operation}`,
        {
          params: { a, b },
        }
      );
      console.log("response", response.data);
      setResult(response.data.result); // Atualiza o resultado com o valor retornado
    } catch (err) {
      setError(err.response?.data?.message || "Erro ao calcular");
    }
  };

  return (
    <div style={{ textAlign: "center", padding: "20px" }}>
      <h1>Calculator</h1>
      <div>
        <input
          type="number"
          placeholder="Value A"
          value={a}
          onChange={(e) => setA(e.target.value)}
        />
        <input
          type="number"
          placeholder="Value B"
          value={b}
          onChange={(e) => setB(e.target.value)}
        />
      </div>
      <div>
        <select
          value={operation}
          onChange={(e) => setOperation(e.target.value)}
        >
          <option value="add">Add</option>
          <option value="subtract">Subtract</option>
          <option value="multiply">Multiply</option>
          <option value="divide">Divide</option>
        </select>
      </div>
      <button onClick={handleCalculate}>Calculate</button>
      <div>
        {result !== null && <h2>Result: {result}</h2>}
        {error && <p style={{ color: "red" }}>{error}</p>}
      </div>
    </div>
  );
};

export default Calculator;
