import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SigoTestModule } from '../../../test.module';
import { MateriaPrimaComponent } from 'app/entities/materia-prima/materia-prima.component';
import { MateriaPrimaService } from 'app/entities/materia-prima/materia-prima.service';
import { MateriaPrima } from 'app/shared/model/materia-prima.model';

describe('Component Tests', () => {
  describe('MateriaPrima Management Component', () => {
    let comp: MateriaPrimaComponent;
    let fixture: ComponentFixture<MateriaPrimaComponent>;
    let service: MateriaPrimaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SigoTestModule],
        declarations: [MateriaPrimaComponent],
      })
        .overrideTemplate(MateriaPrimaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MateriaPrimaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MateriaPrimaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MateriaPrima(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.materiaPrimas && comp.materiaPrimas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
