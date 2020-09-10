import { IFornece } from 'app/shared/model/fornece.model';

export interface IFornecedor {
  id?: number;
  nome?: string;
  cnpj?: string;
  email?: string;
  telefone?: string;
  razaoSocial?: string;
  forneces?: IFornece[];
}

export class Fornecedor implements IFornecedor {
  constructor(
    public id?: number,
    public nome?: string,
    public cnpj?: string,
    public email?: string,
    public telefone?: string,
    public razaoSocial?: string,
    public forneces?: IFornece[]
  ) {}
}
