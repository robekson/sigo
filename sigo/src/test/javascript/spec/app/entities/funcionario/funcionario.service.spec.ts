import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FuncionarioService } from 'app/entities/funcionario/funcionario.service';
import { IFuncionario, Funcionario } from 'app/shared/model/funcionario.model';

describe('Service Tests', () => {
  describe('Funcionario Service', () => {
    let injector: TestBed;
    let service: FuncionarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IFuncionario;
    let expectedResult: IFuncionario | IFuncionario[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FuncionarioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Funcionario(0, 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataNascimento: currentDate.format(DATE_FORMAT),
            dataAdmissao: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Funcionario', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataNascimento: currentDate.format(DATE_FORMAT),
            dataAdmissao: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNascimento: currentDate,
            dataAdmissao: currentDate,
          },
          returnedFromService
        );

        service.create(new Funcionario()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Funcionario', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dataNascimento: currentDate.format(DATE_FORMAT),
            dataAdmissao: currentDate.format(DATE_FORMAT),
            cpf: 'BBBBBB',
            rg: 'BBBBBB',
            sexo: 'BBBBBB',
            loginAcesso: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNascimento: currentDate,
            dataAdmissao: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Funcionario', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            dataNascimento: currentDate.format(DATE_FORMAT),
            dataAdmissao: currentDate.format(DATE_FORMAT),
            cpf: 'BBBBBB',
            rg: 'BBBBBB',
            sexo: 'BBBBBB',
            loginAcesso: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNascimento: currentDate,
            dataAdmissao: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Funcionario', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
