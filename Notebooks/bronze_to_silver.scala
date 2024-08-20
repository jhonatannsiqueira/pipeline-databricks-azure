// Databricks notebook source
// MAGIC %md
// MAGIC **Conferindo se os Dados foram montados e se temos acesso a Pasta Bronze**

// COMMAND ----------

// MAGIC %python
// MAGIC dbutils.fs.ls("/mnt/dados/bronze")

// COMMAND ----------

// MAGIC %md
// MAGIC **Lendo os Dados na Camada Bronze**

// COMMAND ----------

val path = "dbfs:/mnt/dados/bronze/dataset_imoveis/"
val df = spark.read.format("delta").load(path)

// COMMAND ----------

display(df)

// COMMAND ----------

// MAGIC %md
// MAGIC **Transformando os Campos do Json em Coluna**

// COMMAND ----------

display(df.select("anuncio.*"))

// COMMAND ----------

display(
  df.select("anuncio.*", "anuncio.endereco.*")
  )

// COMMAND ----------

val dados_detalhados = df.select("anuncio.*", "anuncio.endereco.*")

// COMMAND ----------

display(dados_detalhados)

// COMMAND ----------

// MAGIC %md
// MAGIC **Removendo Colunas**

// COMMAND ----------

val df_silver = dados_detalhados.drop("caracteristicas", "endereco")
display(df_silver)

// COMMAND ----------

// MAGIC %md
// MAGIC **Salvando na Camada Silver**

// COMMAND ----------

val path = "dbfs:/mnt/dados/silver/dataset_imoveis"
df_silver.write.format("delta").mode("overwrite").save(path)
