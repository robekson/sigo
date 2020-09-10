export interface IMateriaPrima {
  id?: number;
  tipo?: string;
  composicao?: string;
  fio?: string;
  produtoId?: number;
  forneceId?: number;
}

export class MateriaPrima implements IMateriaPrima {
  constructor(
    public id?: number,
    public tipo?: string,
    public composicao?: string,
    public fio?: string,
    public produtoId?: number,
    public forneceId?: number
  ) {}
}
