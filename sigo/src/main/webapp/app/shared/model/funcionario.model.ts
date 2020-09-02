import { Moment } from 'moment';
import { IVenda } from 'app/shared/model/venda.model';

export interface IFuncionario {
  id?: number;
  nome?: string;
  dataNascimento?: Moment;
  dataAdmissao?: Moment;
  cpf?: string;
  rg?: string;
  sexo?: string;
  loginAcesso?: string;
  vendas?: IVenda[];
}

export class Funcionario implements IFuncionario {
  constructor(
    public id?: number,
    public nome?: string,
    public dataNascimento?: Moment,
    public dataAdmissao?: Moment,
    public cpf?: string,
    public rg?: string,
    public sexo?: string,
    public loginAcesso?: string,
    public vendas?: IVenda[]
  ) {}
}
