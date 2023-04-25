import tkinter
from tkinter import messagebox
import os
import subprocess
import jpype

COR_DE_FUNDO = "#DDFFBB"
os.path.abspath(os.path.dirname(__file__))
subprocess.call('javac -source 1.8 -target 1.8 Create.java')
subprocess.call('javac -source 1.8 -target 1.8 Cria_Arquivo.java')
subprocess.call('javac -source 1.8 -target 1.8 Delete.java')
subprocess.call('javac -source 1.8 -target 1.8 Ordenacao_Externa.java')
subprocess.call('javac -source 1.8 -target 1.8 Quick_Sort.java')
subprocess.call('javac -source 1.8 -target 1.8 Read.java')
subprocess.call('javac -source 1.8 -target 1.8 TableInfo.java')
subprocess.call('javac -source 1.8 -target 1.8 Update.java')
subprocess.call('javac -source 1.8 -target 1.8 Aux_Updt.java')



# Inicia a JVM do Java
jpype.startJVM()



class GUI:
    def __init__(self):
        self.tela = tkinter.Tk()
        self.tela.title("Interface Gráfica TP1")
        self.tela.config(padx=5, pady=9, bg=COR_DE_FUNDO)
        self.tela.geometry("650x400")
    

    
        label_titulo = tkinter.Label(text='CRUD e Ordenação Externa', font=("Arial", 20, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_titulo.grid(row=0, column=1, columnspan=3,pady=15, padx=25)
        
        self.botao_create = tkinter.Button(text="Create", font=("Arial", 15, "bold"), fg="#454545", bg="#FFF4E0", width=7, command=self.tela_create)
        self.botao_create.grid(row=1, column=0, pady=75, padx=25)
        
        self.botao_read = tkinter.Button(text="Read", font=("Arial", 15, "bold"),fg="#454545", bg="#FFF4E0", width=7, command=self.tela_read)
        self.botao_read.grid(row=1, column=1, padx=25)
        
        self.botao_update = tkinter.Button(text="Update", font=("Arial", 15, "bold"),fg="#454545", bg="#FFF4E0", width=7, command=self.tela_update)
        self.botao_update.grid(row=1, column=2, padx=25)
        
        self.botao_delete = tkinter.Button(text="Delete", font=("Arial", 15, "bold"),fg="#454545", bg="#FFF4E0", width=7, command=self.tela_delete)
        self.botao_delete.grid(row=1, column=3, padx=25)
        
        self.botao_ordenacao = tkinter.Button(text="Ordenação Externa", font=("Arial", 15, "bold"), fg="#454545", bg="#FFF4E0", width=20, command=self.tela_ordenacao)
        self.botao_ordenacao.grid(row=2, column=1, columnspan=2, padx=5)
        
        self.botao_gera_db = tkinter.Button(text="Gerar DB", font=("Arial", 15, "bold"), fg="#454545", bg="#FFF4E0", width=12, command=self.tela_db)
        self.botao_gera_db.place(x=10, y=350)
        
        self.tela.mainloop()
        
        
    def tela_db(self):
        self.tela.destroy()
        tela = tkinter.Tk()
        tela.title("Cria Arquivo DB")
        tela.config(padx=5, pady=9, bg=COR_DE_FUNDO)
        tela.geometry("650x400")
        
        label = tkinter.Label(text="Arquivo DB", font=("Arial", 15, "bold"),fg="#454545", bg=COR_DE_FUNDO)
        label.place(x=250, y=50)
        
        botao = tkinter.Button(text="Gerar Base de Dados", font=("Arial", 15, "bold"), fg="#454545", bg="#FFF4E0", width=20, command=self.gera_base_dados)
        botao.place(x=175, y=150)
        
        botao_voltar = tkinter.Button(text="Voltar", font=("Arial", 15, "bold"), bg="#FFF4E0", width=10, 
                                command=lambda: self.fecha_tela_atual(tela))
        botao_voltar.place(x=20, y=325)
            
    def tela_ordenacao(self):
        self.tela.destroy()
        
        tela = tkinter.Tk()
        tela.title("Ordenação Externa")
        tela.config(padx=5, pady=9, bg=COR_DE_FUNDO)
        tela.geometry("650x400")
        
        label_ordena = tkinter.Label(text="Ordenação Externa", font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_ordena.grid(row=0, column=0, padx=200)
        
        botao_ordena = tkinter.Button(text="Ordenar Arquivo", font=("Arial", 15, "bold"), fg="#454545", bg="#FFF4E0", width=15, command=self.ordenacao_externa_java)
        botao_ordena.grid(row=1, column=0, pady=50)
        
        botao_voltar = tkinter.Button(text="Voltar", font=("Arial", 15, "bold"), bg="#FFF4E0", width=10, 
                                      command=lambda: self.fecha_tela_atual(tela))
        botao_voltar.grid(row=3, column=0, pady=80)
        
        tela.mainloop()
        
    
    def tela_create(self):
        self.tela.destroy()
        tela = tkinter.Tk()
        tela.title("Create")
        tela.config(padx=5, pady=9, bg=COR_DE_FUNDO)
        tela.geometry("920x600")
        
        label_titulo = tkinter.Label(text="Create",font=("Arial", 25, "bold"), fg="#0EA293", bg=COR_DE_FUNDO)
        label_titulo.grid(row=0, column=0, pady=5, columnspan=2)
        
        
        label_tipo = tkinter.Label(text="Tipo",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_tipo.grid(row=1, column=0)
        
        input_tipo = tkinter.Entry(width=30, font=('Georgia'))
        input_tipo.grid(row=2, column=0)
        
        label_titulo = tkinter.Label(text="Titulo",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_titulo.grid(row=3, column=0)
        
        input_titulo = tkinter.Entry(width=30, font=('Georgia'))
        input_titulo.grid(row=4, column=0)
        
        label_diretor = tkinter.Label(text="Diretor",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_diretor.grid(row=5, column=0)
        
        input_diretor = tkinter.Entry(width=30, font=("Georgia"))
        input_diretor.grid(row=6,column=0)
        
        label_elenco = tkinter.Label(text="Elenco",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_elenco.grid(row=7, column=0)
        
        input_elenco = tkinter.Entry(width=30, font=("Georgia"))
        input_elenco.grid(row=8, column=0)        
        
        label_pais = tkinter.Label(text="País",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_pais.grid(row=9, column=0)
        
        input_pais = tkinter.Entry(width=30, font=("Georgia"))
        input_pais.grid(row=10, column=0)
        
        label_ano_add = tkinter.Label(text="Ano adicionado",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_ano_add.grid(row=1, column=1)
        
        input_ano_add = tkinter.Entry(width=30, font=("Georgia"))
        input_ano_add.grid(row=2, column=1, padx=20)
        
        label_ano_lancado = tkinter.Label(text="Ano Lançado",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_ano_lancado.grid(row=3, column=1, pady=5)
        
        input_ano_lancado = tkinter.Entry(width=30, font=("Georgia"))
        input_ano_lancado.grid(row=4, column=1)
        
        label_avaliacao = tkinter.Label(text="Avaliação",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_avaliacao.grid(row=5, column=1)
        
        input_avaliacao = tkinter.Entry(width=30, font=("Georgia"))
        input_avaliacao.grid(row=6, column=1)
        
        label_duracao = tkinter.Label(text="Duração",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)   
        label_duracao.grid(row=7, column=1)
        
        input_duracao = tkinter.Entry(width=30, font=("Georgia"))
        input_duracao.grid(row=8, column=1)
        
        botao_voltar = tkinter.Button(text="Voltar", font=("Arial", 15, "bold"), bg="#FFF4E0", width=10, 
                                      command=lambda: self.fecha_tela_atual(tela))
        botao_voltar.place(x=100, y=400)
        
        botao_create = tkinter.Button(text="Create", font=("Arial", 15, "bold"), bg="#FFF4E0", width=20,
                                      command=lambda: self.create_java([input_tipo.get(),input_titulo.get(),input_diretor.get(),
                                                                        input_elenco.get(),input_pais.get(),input_ano_add.get(),
                                                                        input_ano_lancado.get(),input_avaliacao.get(), input_duracao.get()]))
        botao_create.place(x=350, y=400)
        
        tela.mainloop()
        
    def create_java(self, lista_elementos):
        z = 1
        for i in range(len(lista_elementos)):
            if len(lista_elementos[i]) == 0:
                z = 0
                i = 11
        
        if z == 1:
            Classe_java = jpype.JClass("Create")
            Classe_java(lista_elementos)
            messagebox.showinfo(title="Sucesso", message="Registro Criado com sucesso")
        else:
            messagebox.showerror(title="Erro", message="Favor, preencher todos os campos.\n Caso não tenha informação de algum, deixar em branco")
        
    
    def tela_read(self):
        self.tela.destroy()
        
        tela = tkinter.Tk()
        tela.title("Read")
        tela.config(padx=5, pady=9, bg=COR_DE_FUNDO)
        tela.geometry("920x800")
        
        label_read = tkinter.Label(text="Read",font=("Arial", 15, "bold"), fg="#0EA293", bg=COR_DE_FUNDO)
        label_read.place(x=400, y=10)
        
        id = tkinter.Label(text="Entre com o ID",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        id.place(x=350, y=75)
        
        input_id = tkinter.Entry(width=30, font=("Georgia"))
        input_id.place(x=300, y=125)
        
        botao_read = tkinter.Button(text="Read", font=("Arial", 15, "bold"), bg="#FFF4E0", width=20, 
                                    command=lambda: self.mostra_read(input_id.get(), tela))
        botao_read.place(x=300, y=175)
        
        botao_voltar = tkinter.Button(text="Voltar", font=("Arial", 15, "bold"), bg="#FFF4E0", width=10, 
                                      command=lambda: self.fecha_tela_atual(tela))
        botao_voltar.place(x=20, y=725)
        
        self.label_id = tkinter.Label(text="",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO) 
        self.label_id.place(x=250, y=250)
  
    
    def tela_update(self):
        self.tela.destroy()
        tela = tkinter.Tk()
        tela.title("Update")
        tela.config(padx=5, pady=9, bg=COR_DE_FUNDO)
        tela.geometry("920x800")
        
        label_titulo = tkinter.Label(text="Update",font=("Arial", 25, "bold"), fg="#0EA293", bg=COR_DE_FUNDO)
        label_titulo.grid(row=0, column=0, pady=5, columnspan=2)
        
        label_tipo = tkinter.Label(text="Tipo",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_tipo.grid(row=1, column=0)
        
        input_tipo = tkinter.Entry(width=30, font=('Georgia'))
        input_tipo.grid(row=2, column=0)
        
        label_titulo = tkinter.Label(text="Titulo",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_titulo.grid(row=3, column=0)
        
        input_titulo = tkinter.Entry(width=30, font=('Georgia'))
        input_titulo.grid(row=4, column=0)
        
        label_diretor = tkinter.Label(text="Diretor",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_diretor.grid(row=5, column=0)
        
        input_diretor = tkinter.Entry(width=30, font=("Georgia"))
        input_diretor.grid(row=6,column=0)
        
        label_elenco = tkinter.Label(text="Elenco",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_elenco.grid(row=7, column=0)
        
        input_elenco = tkinter.Entry(width=30, font=("Georgia"))
        input_elenco.grid(row=8, column=0)        
        
        label_pais = tkinter.Label(text="País",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_pais.grid(row=9, column=0)
        
        input_pais = tkinter.Entry(width=30, font=("Georgia"))
        input_pais.grid(row=10, column=0)
        
        label_ano_add = tkinter.Label(text="Ano adicionado",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_ano_add.grid(row=1, column=1)
        
        input_ano_add = tkinter.Entry(width=30, font=("Georgia"))
        input_ano_add.grid(row=2, column=1, padx=20)
        
        label_ano_lancado = tkinter.Label(text="Ano Lançado",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_ano_lancado.grid(row=3, column=1, pady=5)
        
        input_ano_lancado = tkinter.Entry(width=30, font=("Georgia"))
        input_ano_lancado.grid(row=4, column=1)
        
        label_avaliacao = tkinter.Label(text="Avaliação",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_avaliacao.grid(row=5, column=1)
        
        input_avaliacao = tkinter.Entry(width=30, font=("Georgia"))
        input_avaliacao.grid(row=6, column=1)
        
        label_duracao = tkinter.Label(text="Duração",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)   
        label_duracao.grid(row=7, column=1)
        
        input_duracao = tkinter.Entry(width=30, font=("Georgia"))
        input_duracao.grid(row=8, column=1)
        
        label_id = tkinter.Label(text="ID",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_id.grid(row=9, column=1)
        
        input_id = tkinter.Entry(width=30, font=("Georgia"))
        input_id.grid(row=10, column=1)
        
        botao_voltar = tkinter.Button(text="Voltar", font=("Arial", 15, "bold"), bg="#FFF4E0", width=10, 
                                      command=lambda: self.fecha_tela_atual(tela))
        botao_voltar.place(x=30, y=725)
        
        botao_create = tkinter.Button(text="Update", font=("Arial", 15, "bold"), bg="#FFF4E0", width=20,
                                      command=lambda: self.update_java([input_id.get(), input_tipo.get(),input_titulo.get(),input_diretor.get(),
                                                                        input_elenco.get(),input_pais.get(),input_ano_add.get(),
                                                                        input_ano_lancado.get(),input_avaliacao.get(), input_duracao.get()]))
        botao_create.place(x=250, y=400)
        
    def update_java(self, lista_elementos):
        z = 1
        for i in range(len(lista_elementos)):
            if len(lista_elementos[i]) == 0:
                z = 0
                i = 11
        if z == 1:
            id = int(lista_elementos[0])
            Classe_java = jpype.JClass("Aux_Updt")
            x = Classe_java.main(lista_elementos, id)
            if x == 1:
                messagebox.showinfo(title="Sucesso", message="Registro modificado com sucesso!")
            else:
                messagebox.showerror(title="Erro", message=f"Não foi encontrado nenhum ID {lista_elementos[0]} para atualizar")
        else:
            messagebox.showerror(title="Erro", message="Favor, preencher todos os campos para prosseguir")
    
    def tela_delete(self):  
        self.tela.destroy()
        tela = tkinter.Tk()
        tela.title("Create")
        tela.config(padx=5, pady=9, bg=COR_DE_FUNDO)
        tela.geometry("920x800")
        
        label_delete = tkinter.Label(text="Delete",font=("Arial", 25, "bold"), fg="#0EA293", bg=COR_DE_FUNDO)
        label_delete.place(x=390, y=10)
        
        label_id = tkinter.Label(text="Entre com o ID",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label_id.place(x=360, y=75)
        
        input_id_del = tkinter.Entry(width=30, font=("Georgia"))
        input_id_del.place(x=280, y=125)
        
        botao_delete = tkinter.Button(text="Delete", font=("Arial", 15, "bold"), fg="#454545", bg="#FFF4E0", width=20,
                                     command=lambda: self.delete_java(input_id_del.get()))
        botao_delete.place(x=300, y=175)
        
        botao_voltar = tkinter.Button(text="Voltar", font=("Arial", 15, "bold"), bg="#FFF4E0", width=10, 
                                      command=lambda: self.fecha_tela_atual(tela))
        botao_voltar.place(x=20, y=725)
        
    def delete_java(self, id):  
        if id == '':
            messagebox.showerror(title="Erro", message="Favor, preencher o campo correspondente ao ID")
        else:
            id = int(id)
            Classe_java = jpype.JClass("Delete")
            lista_elementos = Classe_java.deleta(id)
            if lista_elementos[0]!="-1":
                self.preenche_gui(lista_elementos, 0)
            else:
                messagebox.showerror(title="Erro", message="Não foi encontrado nenhum registro com esse ID")
            
    def fecha_tela_atual(self, tela,):
        tela.destroy()
        self.__init__() 
    
    def mostra_read(self, id, tela):
        if id == '':
            messagebox.showerror(title="Erro", message="Favor, preencher o campo correspondente ao ID")
        else:
            id = int(id)
            Classe_java = jpype.JClass("Read")
            lista_elementos = Classe_java.leitura(id)
            self.label_id = tkinter.Label(tela)
            if(lista_elementos[0]!="-1"):
                self.preenche_gui(lista_elementos, 1)
            else:
                messagebox.showerror(title="Erro", message="Não foi encontrado nenhum registro com esse ID")
                
    def preenche_gui(self, lista_elementos, aux):
        lista_chave = ["Tipo", "Titulo", "Diretor", "Elenco", "País", "Ano adicionado", "Ano Lançado", "Avaliação", "Duração"]
        z = 250
        if aux !=1:
            lb = tkinter.Label(text="Registro deletado: ",font=("Arial", 12, "bold"), fg="#454545", bg=COR_DE_FUNDO)
            lb.place(x=250, y=220)
        for i in range(9):
            label = tkinter.Label(text=f"{lista_chave[i]}: {lista_elementos[i]}", font=("Arial", 12, "bold"), fg="#454545", bg=COR_DE_FUNDO)
            label.place(x=250, y=z)
            z+=40
            
    def ordenacao_externa_java(self):  
        Classe_java = jpype.JClass("Ordenacao_Externa")
        Classe_java()
        label = tkinter.Label(text="Ordenação Realizada com sucesso!",font=("Arial", 15, "bold"), fg="#454545", bg=COR_DE_FUNDO)
        label.place(x=150, y=170)
        
    def gera_base_dados(self):
        Classe_java = jpype.JClass("Cria_Arquivo")
        Classe_java.cria_db()
        messagebox.showinfo(title="Sucesso", message="Base de dados gerada com sucesso!")
        
                           

try:
    if os.path.isfile("arq1.db"):
        os.remove("arq1.db")
        os.remove("arq2.db")
    else:
        os.remove("arq3.db")
        os.remove("arq4.db")
except Exception:
    pass

x = GUI()
jpype.shutdownJVM()
